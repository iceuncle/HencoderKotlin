package com.example.lesson

import com.example.core.http.EntityCallback
import com.example.core.http.HttpClient
import com.example.core.utils.Utils
import com.example.lesson.entity.Lesson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList


class LessonPresenter(private var activity: LessonActivity?) {

    companion object {
        const val LESSON_PATH = "lessons"
    }

    private var lessons: List<Lesson> = ArrayList<Lesson>()

    private val type = object : TypeToken<List<Lesson>>() {}.type

    fun fetchData() {
        HttpClient.get(LESSON_PATH, type, object : EntityCallback<List<Lesson>> {
            override fun onSuccess(entity: List<Lesson>) {
                this@LessonPresenter.lessons = entity
                activity!!.runOnUiThread { activity!!.showResult(entity) }
            }

            override fun onFailure(message: String?) {
                activity!!.runOnUiThread { Utils.toast(message) }
            }
        })
    }

    fun showPlayback() {
        activity!!.showResult(lessons.filter {
            it.state === Lesson.State.PLAYBACK
        })
    }
}