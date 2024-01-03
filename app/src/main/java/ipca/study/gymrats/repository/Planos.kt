package ipca.study.gymrats.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Entity
data class Planos(
    @PrimaryKey
    var id : String,
    var trainingType : String,
    var date : Date,
    var isChecked: Boolean = false
)

@Dao
interface PlanosDao {

    @Query("SELECT * FROM Planos")
    fun getAll() : LiveData<List<Planos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(plano: Planos)

    @Update
    fun update(plano: Planos)

    @Delete
    fun delete(plano: Planos)

    @Query("DELETE FROM Planos")
    fun deleteAll()

}