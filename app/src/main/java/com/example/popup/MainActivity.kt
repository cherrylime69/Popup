package com.example.popup

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private lateinit var popUpMenuBtn: AppCompatButton
    private lateinit var popUpMenuStyleBtn: AppCompatButton
    private lateinit var popUpWindowBtn: AppCompatButton
    private lateinit var actionModePriBtn: AppCompatButton
    private lateinit var actionModeFloatingBtn: AppCompatButton
    private lateinit var contextMenuBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popUpMenuBtn = findViewById(R.id.btn_pop_up_menu)
        popUpMenuStyleBtn = findViewById(R.id.btn_pop_up_menu_style)
        popUpWindowBtn = findViewById(R.id.btn_pop_up_window)
        actionModePriBtn = findViewById(R.id.btn_action_mode_primary)
        actionModeFloatingBtn = findViewById(R.id.btn_action_mode_floating)
        contextMenuBtn = findViewById(R.id.btn_context_menu)

        registerForContextMenu(contextMenuBtn)

        setPopUpMenu()
        setPopUpMenuWithCustomStyle()
        setPopUpWindow()
        setActionModePrimary()
        setActionModeFloating()
    }

    private fun setPopUpMenu() {
        val popupMenu = PopupMenu(this, popUpMenuBtn)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu, popupMenu.menu)

        popUpMenuBtn.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun setPopUpMenuWithCustomStyle() {
        val contextWrapper = ContextThemeWrapper(this, R.style.menuStyle)
        val popupMenu = PopupMenu(contextWrapper, popUpMenuBtn, Gravity.END)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu, popupMenu.menu)

        popUpMenuStyleBtn.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun setPopUpWindow() {
        val popupWindow = PopupWindow(
            popUpWindowBtn,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.sample_popup_window, null)
        popupWindow.contentView = popupView
        popupWindow.isTouchable = true
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popUpWindowBtn.setOnClickListener {
            popupWindow.showAtLocation(popUpMenuBtn, Gravity.CENTER, 0, -200)
        }
    }

    private fun setActionModePrimary() {
        actionModePriBtn.setOnClickListener {
            it.startActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    val inflater: MenuInflater? = mode?.menuInflater
                    inflater?.inflate(R.menu.menu, menu)
                    mode?.title = "테스트입니다"
                    mode?.subtitle = "반가워요"
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.menu_copy -> {
                            // "copy" 메뉴를 선택했을 때 동작 구현
                            true
                        }
                    }
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                }

            }, ActionMode.TYPE_PRIMARY)
        }
    }

    private fun setActionModeFloating() {
        actionModeFloatingBtn.setOnClickListener {
            it.startActionMode(object : ActionMode.Callback2() {

                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    val inflater: MenuInflater? = mode?.menuInflater
                    inflater?.inflate(R.menu.menu, menu)
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.menu_copy -> {
                            // "copy" 메뉴를 선택했을 때 동작 구현
                            mode?.finish()
                            true
                        }
                    }
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                }

            }, ActionMode.TYPE_FLOATING)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        if (v?.id == R.id.btn_context_menu) {
            menuInflater.inflate(R.menu.menu, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_copy -> {
                // 복사 메뉴 선택했을 때 로직 구현
            }
        }
        return super.onContextItemSelected(item)
    }


}

