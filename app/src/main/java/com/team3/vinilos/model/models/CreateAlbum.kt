package com.team3.vinilos.model.models

import com.team3.vinilos.model.serializer.DateSerializer
import kotlinx.serialization.Serializable
import org.apache.commons.validator.routines.UrlValidator
import java.util.Date

@Serializable
data class CreateAlbum(
    val name: String? = null,
    val cover: String? = null,
    @Serializable(with = DateSerializer::class)
    val releaseDate: Date? = null,
    val description: String? = null,
    val genre: String? = null,
    val recordLabel: String? = null,
) {
    fun isValid(): Boolean {
        return !(name.isNullOrBlank() ||
                cover.isNullOrBlank() ||
                releaseDate == null ||
                description.isNullOrBlank() ||
                genre.isNullOrBlank() ||
                recordLabel.isNullOrBlank()) ||
                isValidUrl(cover ?: "")
    }

    fun isValidUrl(url: String): Boolean {
        val schemes = arrayOf("http", "https")
        val urlValidator = UrlValidator(schemes)
        return urlValidator.isValid(url)
    }
}