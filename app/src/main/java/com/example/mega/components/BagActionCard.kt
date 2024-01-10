package com.example.mega.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mega.R

@Composable
fun BagActionCard(action: BagActionModel, onDelete:()->Unit){
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
                Text(text = action.name)
                Text(text = "${action.price} USD")
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "${"%.2f".format(action.price * action.count)} USD",
                modifier = Modifier.padding(end = 10.dp)
            )
            IconButton(onClick = onDelete) {
                Image(painter = painterResource(R.drawable.baseline_delete_24), contentDescription = "Удалить")
            }
        }
    }
}


