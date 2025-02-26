package jeonghwan.app.gpa.ui.screen.navi.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jeonghwan.app.entity.GenderType
import jeonghwan.app.entity.PersonEntity

@Composable
fun FavoriteItem(person: PersonEntity) {
    Card(
        modifier =
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // 이름과 성별 아이콘 Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = person.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Gender Icon",
                    tint =
                        when (person.genderType) {
                            GenderType.Male -> Color.Blue
                            GenderType.Female -> Color.Magenta
                            else -> Color.Gray
                        },
                    modifier =
                        Modifier
                            .size(24.dp),
                )
            }

            // 가족 정보
            Text(
                text = "Family: ${person.family}",
                fontSize = 16.sp,
                color = Color.DarkGray,
            )

            // 생존 여부
            Text(
                text = "Alive: ${if (person.alive) "Alive" else "Deceased"}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (person.alive) Color(0xFF2E7D32) else Color(0xFFC62828),
            )
        }
    }
}
