package edu.victoraracil.pr_clase_07.data.local

import edu.victoraracil.pr_clase_07.data.model.Editorial

fun Editorial.toEntity(): EditorialFavoritaEntity {
    return EditorialFavoritaEntity(
        id = this.id,
        nombre = this.editorial,
        url = this.url,
        logoUrl = this.logo
    )
}
