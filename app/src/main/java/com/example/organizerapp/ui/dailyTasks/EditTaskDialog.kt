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
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.edit_task_dialog, null)
        builder.setView(view)
                .setTitle("Edit Task")
                .setNegativeButton("cancel", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->

                })
                .setPositiveButton("OK", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->

                })

        builder.show()
        if (view != null) {
            editTask = view.findViewById(R.id.edit_text)
        }

        return builder.create()
    }

}
