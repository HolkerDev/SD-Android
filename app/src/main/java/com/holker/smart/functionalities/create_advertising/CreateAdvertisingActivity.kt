package com.holker.smart.functionalities.create_advertising

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.holker.smart.R
import com.holker.smart.databinding.ActivityCreateAdvertisingBinding
import com.holker.smart.di.Injectable
import com.holker.smart.di.ViewModelInjectionFactory
import com.holker.smart.functionalities.create_advertising.models.AudienceListAdapter
import com.holker.smart.functionalities.create_advertising.models.DeviceSelectListAdapter
import kotlinx.android.synthetic.main.activity_create_advertising.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class CreateAdvertisingActivity : AppCompatActivity(), Injectable {
    private val _TAG = CreateAdvertisingActivity::class.java.name

    private lateinit var _binding: ActivityCreateAdvertisingBinding
    private lateinit var _viewModel: CreateAdvertisingVM
    private lateinit var _sharedPref: SharedPreferences

    private lateinit var _adapterAudience: AudienceListAdapter
    private lateinit var _adapterDevices: DeviceSelectListAdapter


    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<CreateAdvertisingVM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _sharedPref = applicationContext.getSharedPreferences(
            getString(R.string.preference_key),
            Context.MODE_PRIVATE
        )
        initBinding()

        //set token
        val token = _sharedPref.getString("token", "")!!

        _viewModel.token = token

        //set up adapters
        _adapterAudience = AudienceListAdapter(listOf())

        activity_create_advertising_rv_audiences.layoutManager =
            GridLayoutManager(applicationContext, 1)

        activity_create_advertising_rv_audiences.adapter = _adapterAudience

        _adapterDevices = DeviceSelectListAdapter(listOf())

        activity_create_advertising_rv_devices.layoutManager =
            GridLayoutManager(applicationContext, 1)

        activity_create_advertising_rv_devices.adapter = _adapterDevices


        initObservables()

        _viewModel.pullAudienceSelectList()
        _viewModel.pullDeviceSelectList()
    }

    fun initBinding() {
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_create_advertising)
        _viewModel =
            ViewModelProviders.of(this, viewModelInjectionFactory)
                .get(CreateAdvertisingVM::class.java)
        _binding.viewModel = _viewModel
    }

    fun initObservables() {
        _viewModel.event.observe(this, Observer { event ->
            when (event) {
                CreateAdvertisingState.UploadImage -> {
                    if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            checkSelfPermission(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) != PackageManager.PERMISSION_GRANTED
                        } else {
                            TODO("VERSION.SDK_INT < M")
                        }
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                3
                            )
                        }
                    }
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, 2)
                }
                CreateAdvertisingState.CreatedSuccessful -> {
                    finish()
                }
                is CreateAdvertisingState.Error -> {
                    Toast.makeText(applicationContext, event.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        _viewModel.audienceList.observe(this, Observer { list ->
            Log.i(_TAG, "Apply list to adapter")
            _adapterAudience.items = list
            _adapterAudience.notifyDataSetChanged()
        })

        _viewModel.devicesList.observe(this, Observer { list ->
            _adapterDevices.items = list
            _adapterDevices.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2 && data != null) {
            val selectedImage: Uri = data.data!!
            val filePathColumn =
                arrayOf(MediaStore.Images.Media.DATA)

            val cursor = contentResolver.query(
                selectedImage,
                filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            val imageFile = File(picturePath)

            val requestFile = RequestBody.create(
                MediaType.parse(contentResolver.getType(selectedImage)!!),
                imageFile
            )
            val imageMultiPartFormat =
                MultipartBody.Part.createFormData("advertising", imageFile.name, requestFile);

            _viewModel.imageFile = imageFile
            _viewModel.imageUri = selectedImage
            _viewModel.imagePartData = imageMultiPartFormat
        }
    }

}