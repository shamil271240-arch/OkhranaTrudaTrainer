
package com.example.okhranatruda;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout layout;
    TextView questionText, progressText;
    RadioGroup answers;
    Button nextButton;

    int current = 0;
    int correct = 0;

    String[] questions = {
        "Заболевание считают профессиональным, если оно:",
        "Имеет ли работник право участвовать в расследовании своего профессионального заболевания?",
        "В какие сроки расследуются несчастные случаи, о которых не было своевременно сообщено работодателю?",
        "Кем утверждается акт о несчастном случае после завершения расследования?",
        "В какой срок работодатель должен образовать комиссию по расследованию профессионального заболевания?"
    };

    String[][] options = {
        {
            "Получено рабочим-профессионалом",
            "Вызвано любыми вредными факторами",
            "Вызвано профессиональными вредностями и соответствует списку профзаболеваний",
            "Соответствует списку независимо от условий получения"
        },
        {
            "Да",
            "Только при остром заболевании",
            "Нет"
        },
        {
            "15 дней",
            "1 месяц",
            "1 год"
        },
        {
            "Председателем комиссии",
            "Руководителем охраны труда",
            "Работодателем"
        },
        {
            "3 дня",
            "2 недели",
            "10 дней"
        }
    };

    int[] right = {2, 0, 1, 2, 2};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showQuestion();
    }

    void showQuestion() {
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(35, 45, 35, 25);
        layout.setBackgroundColor(Color.WHITE);

        progressText = new TextView(this);
        progressText.setText("Вопрос " + (current + 1)
                + " из " + questions.length);
        progressText.setTextSize(18);
        progressText.setTextColor(Color.GRAY);
        layout.addView(progressText);

        questionText = new TextView(this);
        questionText.setText(questions[current]);
        questionText.setTextSize(23);
        questionText.setTextColor(Color.BLACK);
        questionText.setPadding(0, 35, 0, 35);
        layout.addView(questionText);

        answers = new RadioGroup(this);

        for (String option : options[current]) {
            RadioButton rb = new RadioButton(this);
            rb.setText(option);
            rb.setTextSize(18);
            rb.setTextColor(Color.BLACK);
            rb.setPadding(5, 18, 5, 18);
            answers.addView(rb);
        }

        layout.addView(answers);

        nextButton = new Button(this);
        nextButton.setText("Проверить ответ");
        layout.addView(nextButton);

        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);

        nextButton.setOnClickListener(v -> checkAnswer());
    }

    void checkAnswer() {
        int selected = answers.getCheckedRadioButtonId();

        if (selected == -1) {
            Toast.makeText(this,
                "Выберите ответ",
                Toast.LENGTH_SHORT).show();
            return;
        }

        int index = answers.indexOfChild(
            answers.findViewById(selected));

        boolean isCorrect = index == right[current];

        if (isCorrect) correct++;

        new android.app.AlertDialog.Builder(this)
            .setTitle(isCorrect ? "Правильно!" : "Ошибка")
            .setMessage(isCorrect
                ? "Отлично! Верный ответ."
                : "Правильный ответ:\n"
                  + options[current][right[current]])
            .setPositiveButton("Далее", (d, w) -> {
                current++;
                if (current < questions.length) {
                    showQuestion();
                } else {
                    showResult();
                }
            })
            .show();
    }

    void showResult() {
        new android.app.AlertDialog.Builder(this)
            .setTitle("Тест завершён")
            .setMessage("Правильных ответов: "
                + correct + " из " + questions.length)
            .setPositiveButton("Пройти заново", (d, w) -> {
                current = 0;
                correct = 0;
                showQuestion();
            })
            .setCancelable(false)
            .show();
    }
}
