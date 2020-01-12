package com.holker.smart.functionalities.create_advertising

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_create_advertising.*
import java.io.File
import javax.inject.Inject


class CreateAdvertisingActivity : AppCompatActivity(), Injectable {
    private val _TAG = CreateAdvertisingActivity::class.java.name

    private lateinit var _binding: ActivityCreateAdvertisingBinding
    private lateinit var _viewModel: CreateAdvertisingVM
    private lateinit var _sharedPref: SharedPreferences

    private lateinit var _adapterAudience: AudienceListAdapter


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
        //set up adapter
        _adapterAudience = AudienceListAdapter(listOf())
        activity_create_advertising_rv_audiences.layoutManager =
            GridLayoutManager(applicationContext, 1)
        activity_create_advertising_rv_audiences.adapter = _adapterAudience

        initObservables()

        _viewModel.pullAudienceSelectList()
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
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, 2)
                }
            }
        })

        _viewModel.audienceList.observe(this, Observer { list ->
            Log.i(_TAG, "Apply list to adapter")
            _adapterAudience.items = list
            _adapterAudience.notifyDataSetChanged()
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
            _viewModel.imageFile = imageFile
        }
    }

}