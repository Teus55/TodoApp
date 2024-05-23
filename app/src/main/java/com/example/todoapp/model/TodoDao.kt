package com.example.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: Todo)

    @Query("SELECT * FROM todo WHERE is_done != 1 ORDER BY priority DESC") //ketambahan where is_done
    fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    fun selectTodo(id: Int): Todo

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority WHERE uuid = :id")
    fun update(title: String, notes: String, priority: Int, id: Int)

    @Delete
    fun deleteTodo(todo: Todo)

    @Query("UPDATE todo SET is_done = 1 WHERE uuid = :id") //untuk merubah is_done
    fun markAsDone(id: Int)

}
