package com.example.listycity3

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onRemoveCity: (City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember {mutableStateOf(false)}
    var editThisCity by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
            FloatingActionButton(modifier = Modifier.padding(16.dp), onClick = {showAddCityFields = !showAddCityFields}){
                Text("+")
            }
        }
        if (showAddCityFields) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                        }
                    }
                ) { Text("Add City") }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(city = city, editThisCity, onAddCity, onRemoveCity,
                    changeState = {
                        if (editThisCity == city.name) {
                            editThisCity = ""
                        }
                        else {
                            editThisCity = city.name
                        }
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(city: City, editThisCity: String, onAddCity: (City) -> Unit, onRemoveCity: (City) -> Unit, changeState: () -> Unit) {

    var changedName by remember { mutableStateOf("") }
    var changeProvince by remember { mutableStateOf("") }


    if (editThisCity != city.name) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = city.name,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = city.province,
                fontSize = 30.sp,
                modifier = Modifier.weight(1f)
            )

            Button(onClick = {changeState()}){
                Text("Edit")
            }
        }
    }
    else {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                OutlinedTextField(
                    value = changedName,
                    //fontSize = 30.sp,
                    onValueChange = { changedName = it },
                    modifier = Modifier.weight(1f).padding(horizontal = 5.dp),
                    label = {Text("City")}
                )

                OutlinedTextField(
                    value = changeProvince,
                    onValueChange = { changeProvince = it },
                    //fontSize = 30.sp,
                    modifier = Modifier.weight(1f).padding(horizontal = 5.dp),
                    label = {Text("Province")}
                )
            }

            Row() {
                Button(
                    onClick = {
                        if (changedName.isBlank()) {
                            changedName = city.name
                        }

                        if (changeProvince.isBlank()) {
                            changeProvince = city.province
                        }

                        onRemoveCity(City(name = city.name, province = city.province))
                        onAddCity(City(name = changedName, province = changeProvince))

                        changedName = ""
                        changeProvince = ""
                        changeState()

                    }
                ) {
                    Text("Save")
                }

                Spacer(modifier = Modifier.width(15.dp))
                Button(onClick = { changeState() }) { Text("Cancel") }
            }

        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {}
        )
    }
}
 */