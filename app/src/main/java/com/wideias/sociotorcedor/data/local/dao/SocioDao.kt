package com.wideias.sociotorcedor.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wideias.sociotorcedor.data.local.entity.SocioEntity

@Dao
interface SocioDao {
    @Query("SELECT * FROM socios WHERE id = :id")
    suspend fun getSocioById(id: String): SocioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSocio(socio: SocioEntity)

    @Query("DELETE FROM socios")
    suspend fun clearAll()
}
