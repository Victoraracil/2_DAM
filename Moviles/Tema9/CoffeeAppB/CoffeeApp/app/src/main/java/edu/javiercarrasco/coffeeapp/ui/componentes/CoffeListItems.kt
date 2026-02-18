package edu.javiercarrasco.coffeeapp.ui.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.javiercarrasco.ejemplologin.data.model.Coffee

@Composable
fun CoffeeListItems(
    coffeeList: List<Coffee>,
    onItemClick: (Coffee) -> Unit
) {
    LazyColumn() {
        items(coffeeList) { coffee ->
            CoffeeItem(
                coffee = coffee,
                onItemClick = {
                    onItemClick(coffee)
                })
        }
    }
}

@Composable
fun CoffeeItem(coffee: Coffee, onItemClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onItemClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = coffee.coffeeName!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.85f),
                fontSize = 20.sp
            )
            Text(
                text = coffee.comments!!,
                modifier = Modifier.wrapContentSize(),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }
    }
}