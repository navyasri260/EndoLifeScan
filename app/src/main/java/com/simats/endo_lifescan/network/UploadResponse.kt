package com.simats.endo_lifescan.network

import com.google.gson.annotations.SerializedName

data class UploadResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("is_endo_file")
    val isEndoFile: Boolean,

    @SerializedName("prediction")
    val prediction: String,

    @SerializedName("recommendation")
    val recommendation: String,

    @SerializedName("confidence")
    val confidence: Double,

    @SerializedName("fatigue_score")
    val fatigueScore: Double,

    @SerializedName("heatmaps")
    val heatmaps: List<String>,

    @SerializedName("segment_results")
    val segmentResults: List<SegmentResult>

)

data class SegmentResult(

    @SerializedName("name")
    val name: String,

    @SerializedName("status")
    val status: String

) {
    fun formattedResult(): String {
        return "$name - $status"
    }
}