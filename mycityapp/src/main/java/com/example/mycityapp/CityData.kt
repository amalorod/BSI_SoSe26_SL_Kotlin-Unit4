package com.example.mycityapp

object LocalDataProvider {
    val allCategories: List<CityCategory> = listOf(
        CityCategory(
            nameRes = R.string.category_cafes,
            recommendations = listOf(
                Recommendation(
                    nameRes = R.string.rec_kaffee_glueck,
                    descriptionRes = R.string.desc_kaffee_glueck,
                    imageRes = R.drawable.kaffee_glueck_image
                )
            )
        ),
        CityCategory(
            nameRes = R.string.category_bakery,
            recommendations = listOf(
                Recommendation(
                    nameRes = R.string.rec_bakery,
                    descriptionRes = R.string.desc_bakery,
                    imageRes = R.drawable.bakery_image
                )
            )
        ),
        CityCategory(
            nameRes = R.string.category_parks,
            recommendations = listOf(
                Recommendation(
                    nameRes = R.string.rec_stadtpark,
                    descriptionRes = R.string.desc_stadtpark,
                    imageRes = R.drawable.stadtpark_image
                )
            )
        ),
        CityCategory(
            nameRes = R.string.category_nature,
            recommendations = listOf(
                Recommendation(
                    nameRes = R.string.rec_nature,
                    descriptionRes = R.string.desc_nature,
                    imageRes = R.drawable.nature_image
                )
            )
        )
    )
}
