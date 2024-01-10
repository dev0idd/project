package com.example.mega.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mega.R

@Composable
fun CompanyShareCard (companyShareModel: CompanyShareModel, onSelect: ()->Unit) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.mock_icon),
                contentDescription = ""
            )   
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxHeight()
                    .width(130.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = companyShareModel.brand)
                Text(text = companyShareModel.ticker)
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "${companyShareModel.price} ${companyShareModel.currency}",
                modifier = Modifier.padding(end = 10.dp)
            )
            Button(
                onClick = {onSelect()},
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "+", fontSize = 32.sp)
            }
        }
    }
}


