package com.example.organizerapp.ui.dailyTasks

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.organizerapp.R

class EditTaskDialog : AppCompatDialogFragment() {
    private lateinit var editTask : EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = layoutInflater.inflate(R.layout.edit_task_dialog, null)
        builder.setView(view)
                .setTitle("Edit Task")
                .setPositiveButton("OK", DialogInterface.OnClickListener{_, _->

                })
                .setNegativeButton("CLOSE", DialogInterface.OnClickListener(){_, _->

                })
        builder.show()

        return builder.create()
    }

}
