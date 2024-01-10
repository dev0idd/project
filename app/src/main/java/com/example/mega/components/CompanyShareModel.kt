package com.example.mega.components

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable

data class CompanyShareModel(
    val id: String,
    val ticker: String,
    val isin: String,
    val currency: String,
    val lotSize: String,
    val exchangeShowName: String,
    val exchangeLogoUrl: String,
    val showName: String,
    val logoName: String,
    val color: String,
    val textColor: String,
    val countryOfRiskBriefName: String,
    val countryOfRiskLogoUrl: String,
    val brand: String,
    val price: Double,
    val isFavorite: Boolean,
    val riskCategory: String,
    val depository: String,
    val depoAccountSection: String
)
