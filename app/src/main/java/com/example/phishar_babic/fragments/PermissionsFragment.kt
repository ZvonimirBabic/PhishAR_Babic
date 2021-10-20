package com.example.phishar_babic.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.phishar_babic.R

class PermissionsFragment : Fragment(R.layout.fragment_permissions) {

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) { // Do something if permission granted
                navigateToCamera()

            } else { // Do something as the permission is not granted
                Log.d("LOG_TAG", "permission denied by the user")
            }
        }

    private val rationaleDialog = AlertDialog.Builder(requireContext())
        .setTitle(R.string.rationaleTitle)
        .setMessage(R.string.rationaleText)
        .setPositiveButton(R.string.allow) { dialog, which ->
            requestPermission.launch(
                Manifest.permission.CAMERA
            )
        }
        .setNegativeButton(R.string.cancel) { dialog, which ->
            dialog.cancel()
            requireActivity().finish()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                rationaleDialog.show()
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermission.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }

    private fun navigateToCamera() {
        lifecycleScope.launchWhenStarted {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                PermissionsFragmentDirections.actionPermissionsFragmentToCameraFragment()
            )
        }
    }
}