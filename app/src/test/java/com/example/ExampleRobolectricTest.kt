package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.CompoundType
import com.example.data.model.PresetQuestions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("QuizMaster", appName)
  }

  @Test
  fun `verify preset chemistry questions repository`() {
    val allQuestions = PresetQuestions.allQuestions
    assertTrue("Debe contener preguntas químicas cargadas", allQuestions.isNotEmpty())

    val organicQuestions = PresetQuestions.getQuestionsByFilter(category = CompoundType.ORGANIC)
    val inorganicQuestions = PresetQuestions.getQuestionsByFilter(category = CompoundType.INORGANIC)

    assertTrue("Debe contener preguntas de compuestos orgánicos", organicQuestions.isNotEmpty())
    assertTrue("Debe contener preguntas de compuestos inorgánicos", inorganicQuestions.isNotEmpty())

    // Verify all questions have 4 options and valid explanations
    for (q in allQuestions) {
      assertEquals("Cada pregunta debe tener 4 opciones", 4, q.options.size)
      assertTrue("El índice correcto debe estar entre 0 y 3", q.correctIndex in 0..3)
      assertTrue("La explicación debe estar redactada", q.explanation.isNotBlank())
      assertTrue("El compuesto debe tener fórmula", q.formula.isNotBlank())
    }
  }
}
