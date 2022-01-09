package com.example.taskmanagement.data.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskMember(

    @PrimaryKey(autoGenerate = true)
    val memberId: Int,
    var taskId: Int,
    val user: User,

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        TODO("user")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(memberId)
        parcel.writeInt(taskId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskMember> {
        override fun createFromParcel(parcel: Parcel): TaskMember {
            return TaskMember(parcel)
        }

        override fun newArray(size: Int): Array<TaskMember?> {
            return arrayOfNulls(size)
        }
    }
}
