package com.dicolimba.testawsreko.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.rekognition.AmazonRekognition
import com.amazonaws.services.rekognition.AmazonRekognitionClient
import com.amazonaws.services.rekognition.model.DetectFacesRequest
import com.amazonaws.services.rekognition.model.Image
import com.dicolimba.testawsreko.secret.Credentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.ByteBuffer

class FaceViewModel : ViewModel() {

    fun requestAWS(byteArray: ByteArray){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val image = Image()
                val request = DetectFacesRequest()
                image.bytes = ByteBuffer.wrap(byteArray)
                request.image = image
                request.withAttributes("EMOTIONS")

                val result = amazonRekognition.detectFaces(request)
                val faceDetails = result.faceDetails
                for (face in faceDetails) {
                    val emotions = face.emotions
                    for (emotion in emotions) {
                        Log.d(TAG, "onActivityResult: " + emotion.type + " " + emotion.confidence)
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "requestAWS: ", e)
            }
        }
    }

    companion object{
        private const val TAG = "FaceViewModel"
        private lateinit var amazonRekognition: AmazonRekognition
    }
    init {
        amazonRekognition = AmazonRekognitionClient(
            BasicAWSCredentials(
                Credentials.ACCESS_KEY,
                Credentials.SECRET_KEY
            )
        )
    }
}