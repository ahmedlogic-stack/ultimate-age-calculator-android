package com.example.agecalculator.adapter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.agecalculator.databinding.CardAgeNowBinding
import com.example.agecalculator.model.AgeCard

class AgeRightNowVH(
    private val binding: CardAgeNowBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AgeCard.AgeRightNow) = with(binding) {
        // Set age numbers
        tvYearsNumber.text = item.years.toString()
        tvMonthsNumber.text = item.months.toString()
        tvDaysNumber.text = item.days.toString()
        textMonthNumber.text = item.monthsOld.toString()
        textWeekNumber.text = item.weeksOld.toString()
        textDayNumber.text = item.daysOld.toString()
        textHourNumber.text = item.hoursOld.toString()
        textMinuteNumber.text = item.minutesOld.toString()
        textSecondNumber.text = item.secondsOld.toString()

        // ===== Save Button =====
        btnSave.setOnClickListener {
            val bitmap = captureView(binding.root)
            val savedUri = saveImageToGallery(binding.root.context, bitmap, "AgeCard_${System.currentTimeMillis()}")
            if (savedUri != null) {
                Toast.makeText(binding.root.context, "Saved to gallery!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(binding.root.context, "Failed to save!", Toast.LENGTH_SHORT).show()
            }
        }

        // ===== Share Button =====
        btnShare.setOnClickListener {
            val bitmap = captureView(binding.root)
            val uri = saveImageToGallery(binding.root.context, bitmap, "AgeCard_${System.currentTimeMillis()}")
            if (uri != null) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/png"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                binding.root.context.startActivity(Intent.createChooser(shareIntent, "Share Age Card"))
            } else {
                Toast.makeText(binding.root.context, "Failed to share!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Capture view as bitmap
    private fun captureView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    // Save bitmap to gallery and return Uri for sharing
    private fun saveImageToGallery(context: Context, bitmap: Bitmap, fileName: String): android.net.Uri? {
        return try {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.png")
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/AgeCalculator")
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }

            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                resolver.openOutputStream(it)?.use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(it, contentValues, null, null)
            }
            uri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
