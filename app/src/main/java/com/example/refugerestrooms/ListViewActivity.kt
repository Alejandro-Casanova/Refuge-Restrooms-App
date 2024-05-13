package com.example.refugerestrooms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.refugerestrooms.data.DummyDataSource
import com.example.refugerestrooms.model.Restroom
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

class ListViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RefugeRestroomsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RestroomListApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RestroomListApp(modifier: Modifier = Modifier) {
    RestroomList(
        restroomsList = DummyDataSource().loadDummyRestrooms(),
        modifier = modifier
    )
}

@Composable
fun RestroomCard(restroom: Restroom, modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.toiletlogo),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 6.dp, top = 6.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = stringResource(restroom.nameStringResourceId),
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    text = stringResource(restroom.addressStringResourceId),
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 14.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(6.dp)
            ) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = stringResource(restroom.distanceStringResourceId),
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 14.sp
                )
                // Optional Icons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ){
                    // Plot Unisex Icon when necessary
                    if(booleanResource(restroom.unisexFlagResourceId)){
                        Image(
                            painter = painterResource(R.drawable.genderneutral),
                            contentDescription = "Unisex / Gender Neutral",
                            modifier = Modifier
                                .size(30.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    // Plot Accessible Icon when necessary
                    if(booleanResource(restroom.accessibleFlagResourceId)){
                        Image(
                            painter = painterResource(R.drawable.accessiblerestroom),
                            contentDescription = "Accessible",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(2.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RestroomList(restroomsList: List<Restroom>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(restroomsList) { restroom ->
            RestroomCard(
                restroom = restroom,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListViewPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RestroomListApp()
//            RestroomCard(
//                Restroom(
//                    R.string.restroom_name_1,
//                    R.string.restroom_address_1,
//                    R.string.restroom_distance_1,
//                    R.bool.restroom_unisex_1,
//                    R.bool.restroom_accessible_1
//                ),
//                modifier = Modifier
//            )
        }
    }
}