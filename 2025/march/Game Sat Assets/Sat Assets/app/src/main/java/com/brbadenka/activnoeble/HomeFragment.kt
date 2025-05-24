package com.brbadenka.activnoeble

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

/**
 * Класс для "Топ инвестиций". Здесь добавляются новые вклады,
 * сохраняются в SharedPreferences (и для самого Топа, и для Архива).
 * Также реализован поиск по названиям.
 */
class HomeFragment : Fragment() {

    private lateinit var investmentContainer: LinearLayout // Контейнер для Топ инвестиций
    private lateinit var historyContainer: LinearLayout    // Отображаем архив внизу (по желанию)
    private lateinit var totalPortfolioTextView: TextView  // Общая сумма портфеля
    private lateinit var totalProfitTextView: TextView     // Общая прибыль
    private lateinit var searchView: SearchView            // Поиск
    private lateinit var showAll: TextView                 // Кнопка/ссылка "Показать всё" (переход в HisFragment)

    private var totalPortfolio: Double = 0.0
    private var totalProfit: Double = 0.0

    private var isCardsLoaded = false

    // Основной список "Топ инвестиций"
    private val cardList = mutableListOf<CardData>()

    // Список для архива (для отображения в этом же фрагменте)
    private val historyList = mutableListOf<CardData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Ищем наши View
        searchView = view.findViewById(R.id.searchView)
        investmentContainer = view.findViewById(R.id.investment_container)
        historyContainer = view.findViewById(R.id.history_container)
        totalPortfolioTextView = view.findViewById(R.id.total_portfolio_value)
        totalProfitTextView = view.findViewById(R.id.month_profit_value)
        showAll = view.findViewById(R.id.showall)  // TextView/кнопка "Показать всё" (архив)

        // Переход в HisFragment по клику на "Показать всё"
        showAll.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HisFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // Градиентная заливка "шапки"
        val topCardView = view.findViewById<CardView>(R.id.cardView)
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.parseColor("#24B23E"), Color.parseColor("#5ACB3A"))
        )
        gradientDrawable.cornerRadius = 20f
        topCardView.background = gradientDrawable

        // Загружаем данные один раз за "жизнь" фрагмента
        if (!isCardsLoaded) {
            // Чтобы не дублировать карточки при повторном заходе
            cardList.clear()
            investmentContainer.removeAllViews()
            historyList.clear()
            historyContainer.removeAllViews()

            loadSavedCards()         // Загружаем "Топ инвестиций"
            loadSavedHistoryCards()  // Загружаем "Архив"
            loadTotalValuesFromPreferences()

            isCardsLoaded = true
        }

        // Если пришли аргументы из PlusFragment (создание/редактирование вклада)
        val name = arguments?.getString("name")
        val period = arguments?.getString("period")
        val percent = arguments?.getString("percent")
        val amount = arguments?.getString("amount")
        val profit = arguments?.getString("profit")
        val date = getCurrentDate()

        // Добавляем новую карточку в Топ + Архив
        if (name != null && period != null && percent != null && amount != null && profit != null) {
            addNewCard(name, period, percent, amount, profit)
            addHistoryCard(name, period, percent, amount, profit, date)
            clearBundle()
        }

        // Подключаем слушатель поиска
        setupSearchListener()

        return view
    }

    /**
     * Настраиваем поиск по названию вклада (по полю name).
     */
    private fun setupSearchListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Можно спрятать клавиатуру
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterCards(newText)
                return true
            }
        })
    }

    /**
     * Фильтр по названию вклада. Сортируем по прибыли (DESC).
     */
    private fun filterCards(query: String?) {
        if (query.isNullOrEmpty()) {
            val sortedList = cardList.sortedByDescending { it.profit.toDouble() }
            updateHorizontalScrollView(sortedList)
        } else {
            val filtered = cardList.filter {
                it.name.contains(query, ignoreCase = true)
            }.sortedByDescending { it.profit.toDouble() }
            updateHorizontalScrollView(filtered)
        }
    }

    /**
     * Добавляем новую карточку в "Топ инвестиций".
     */
    private fun addNewCard(name: String, period: String, percent: String, amount: String, profit: String) {
        saveCard(name, period, percent, amount, profit)
        createTopCard(name, period, percent, amount, profit)
    }

    /**
     * Добавляем запись в "Архив" (сохраняем и отрисовываем).
     */
    private fun addHistoryCard(name: String, period: String, percent: String, amount: String, profit: String, date: String) {
        saveHistoryCard(name, period, percent, amount, profit, date)
        createHistoryCard(name, period, percent, amount, profit, date)
    }

    /**
     * Создаём карточку в "Топ инвестиций" и обновляем экран.
     */
    private fun createTopCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val cardData = CardData(name, period, percent, amount, profit)
        cardList.add(cardData)

        // Пересчитываем сумму портфеля и прибыль
        totalPortfolio += amount.toDouble()
        totalProfit += profit.toDouble()
        updatePortfolioAndProfit()

        // Сортируем и показываем
        val sortedByProfit = cardList.sortedByDescending { it.profit.toDouble() }
        updateHorizontalScrollView(sortedByProfit)
    }

    /**
     * Обновляем горизонтальный список в "Топ инвестиций" (investmentContainer).
     */
    private fun updateHorizontalScrollView(listToShow: List<CardData>) {
        investmentContainer.removeAllViews()

        listToShow.forEach { card ->
            val cardView = LayoutInflater.from(context)
                .inflate(R.layout.card_investment, investmentContainer, false)

            val titleTV = cardView.findViewById<TextView>(R.id.depositTitle)
            val periodTV = cardView.findViewById<TextView>(R.id.depositPeriod)
            val rateTV = cardView.findViewById<TextView>(R.id.depositRate)
            val amountTV = cardView.findViewById<TextView>(R.id.depositAmount)
            val profitTV = cardView.findViewById<TextView>(R.id.depositProfit)
            val editBtn = cardView.findViewById<ImageView>(R.id.editLabel)
            val deleteBtn = cardView.findViewById<ImageView>(R.id.deleteLabel)

            titleTV.text = card.name
            periodTV.text = "${card.period} мес."
            rateTV.text = "${card.percent}%"
            amountTV.text = "${card.amount} ₽"
            profitTV.text = "${card.profit} ₽"

            // Удаление
            deleteBtn.setOnClickListener {
                investmentContainer.removeView(cardView)
                deleteCard(card.name, card.period, card.percent, card.amount, card.profit)
            }

            // Редактирование
            editBtn.setOnClickListener {
                investmentContainer.removeView(cardView)
                deleteCard(card.name, card.period, card.percent, card.amount, card.profit)

                val plusFragment = PlusFragment().apply {
                    arguments = Bundle().apply {
                        putString("name", card.name)
                        putString("period", card.period)
                        putString("percent", card.percent)
                        putString("amount", card.amount)
                        putString("profit", card.profit)
                    }
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, plusFragment)
                    .addToBackStack(null)
                    .commit()
            }

            investmentContainer.addView(cardView)
        }
    }

    /**
     * Создаём карточку для "Архива".
     * ВАЖНО: НЕ добавляем здесь в historyList, чтобы избежать ConcurrentModificationException.
     */
    private fun createHistoryCard(
        name: String,
        period: String,
        percent: String,
        amount: String,
        profit: String,
        date: String
    ) {
        // Только создаём View и добавляем в контейнер
        val cardView = LayoutInflater.from(context)
            .inflate(R.layout.card_investment, historyContainer, false)

        val titleTV = cardView.findViewById<TextView>(R.id.depositTitle)
        val periodTV = cardView.findViewById<TextView>(R.id.depositPeriod)
        val rateTV = cardView.findViewById<TextView>(R.id.depositRate)
        val amountTV = cardView.findViewById<TextView>(R.id.depositAmount)
        val profitTV = cardView.findViewById<TextView>(R.id.depositProfit)

        titleTV.text = name
        periodTV.text = "$period мес."
        rateTV.text = "$percent%"
        amountTV.text = "$amount ₽"
        profitTV.text = "$profit ₽"

        // Добавляем новую карточку сверху
        historyContainer.addView(cardView, 0)
    }

    /**
     * Удаление карточки из SharedPreferences ("cards") + локального списка (cardList).
     */
    private fun deleteCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val sp = requireContext().getSharedPreferences("cards", Context.MODE_PRIVATE)
        val editor = sp.edit()

        val cardCount = sp.getInt("cardCount", 0)
        val newList = mutableListOf<String>()

        // Переписываем все, кроме удаляемого
        for (i in 0 until cardCount) {
            val data = sp.getString("card_$i", null)
            if (data != null && data != "$name|$period|$percent|$amount|$profit") {
                newList.add(data)
            }
        }

        // Перезаписываем обновлённый список
        editor.clear()
        editor.putInt("cardCount", newList.size)
        newList.forEachIndexed { index, s ->
            editor.putString("card_$index", s)
        }
        editor.apply()

        // Удаляем из локального списка
        cardList.removeAll {
            it.name == name && it.period == period && it.percent == percent &&
                    it.amount == amount && it.profit == profit
        }

        totalPortfolio -= amount.toDouble()
        totalProfit -= profit.toDouble()
        updatePortfolioAndProfit()

        // Обновляем UI (сортируем заново)
        val sorted = cardList.sortedByDescending { it.profit.toDouble() }
        updateHorizontalScrollView(sorted)
    }

    /**
     * Сохранение новой карточки в "cards" (SharedPreferences).
     */
    private fun saveCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val sp = requireContext().getSharedPreferences("cards", Context.MODE_PRIVATE)
        val editor = sp.edit()

        val cardCount = sp.getInt("cardCount", 0)
        editor.putString("card_$cardCount", "$name|$period|$percent|$amount|$profit")
        editor.putInt("cardCount", cardCount + 1)
        editor.apply()
    }

    /**
     * Сохранение карточки в "history".
     * Формат: name|period|percent|amount|profit|date
     */
    private fun saveHistoryCard(
        name: String,
        period: String,
        percent: String,
        amount: String,
        profit: String,
        date: String
    ) {
        val sp = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val editor = sp.edit()

        val historyCount = sp.getInt("historyCount", 0)
        editor.putString("history_card_$historyCount", "$name|$period|$percent|$amount|$profit|$date")
        editor.putInt("historyCount", historyCount + 1)
        editor.apply()
    }

    /**
     * Загрузка карточек (топ инвестиций) из "cards".
     */
    private fun loadSavedCards() {
        val sp = requireContext().getSharedPreferences("cards", Context.MODE_PRIVATE)
        val cardCount = sp.getInt("cardCount", 0)

        totalPortfolio = 0.0
        totalProfit = 0.0

        for (i in 0 until cardCount) {
            val data = sp.getString("card_$i", null)
            if (data != null) {
                val parts = data.split("|")
                if (parts.size == 5) {
                    val name = parts[0]
                    val period = parts[1]
                    val percent = parts[2]
                    val amount = parts[3]
                    val profit = parts[4]

                    cardList.add(CardData(name, period, percent, amount, profit))
                    totalPortfolio += amount.toDouble()
                    totalProfit += profit.toDouble()
                }
            }
        }

        // Сортируем по прибыли убыванию
        val sorted = cardList.sortedByDescending { it.profit.toDouble() }
        updateHorizontalScrollView(sorted)

        updatePortfolioAndProfit()
    }

    /**
     * Загрузка карточек (архив) из "history".
     * Используем временный список, чтобы избежать ConcurrentModificationException.
     */
    private fun loadSavedHistoryCards() {
        val sp = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val historyCount = sp.getInt("historyCount", 0)

        // ВРЕМЕННЫЙ список
        val tempList = mutableListOf<CardData>()

        for (i in 0 until historyCount) {
            val data = sp.getString("history_card_$i", null)
            if (data != null) {
                // Формат name|period|percent|amount|profit|date
                val parts = data.split("|")
                if (parts.size == 6) {
                    val name = parts[0]
                    val period = parts[1]
                    val percent = parts[2]
                    val amount = parts[3]
                    val profit = parts[4]
                    val date = parts[5]

                    tempList.add(CardData(name, period, percent, amount, profit, date))
                }
            }
        }

        // Теперь добавляем tempList в historyList за один раз
        historyList.addAll(tempList)

        // Отрисовываем карточки, проходясь по tempList (чтобы не менять historyList в цикле)
        tempList.forEach { item ->
            createHistoryCard(item.name, item.period, item.percent, item.amount, item.profit, item.date)
        }
    }

    /**
     * Подгружаем сохранённые значения портфеля/прибыли (если нужно).
     */
    private fun loadTotalValuesFromPreferences() {
        val sp = requireContext().getSharedPreferences("totals", Context.MODE_PRIVATE)
        // Можно считать сохранённые значения
        val savedPortfolio = sp.getFloat("totalPortfolio", 0f)
        val savedProfit = sp.getFloat("totalProfit", 0f)
        // По желанию — использовать их или нет
        // totalPortfolio = savedPortfolio.toDouble()
        // totalProfit = savedProfit.toDouble()

        updatePortfolioAndProfit()
    }

    /**
     * Сохраняем текущее состояние totalPortfolio / totalProfit в prefs.
     */
    private fun saveTotalValuesToPreferences() {
        val sp = requireContext().getSharedPreferences("totals", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putFloat("totalPortfolio", totalPortfolio.toFloat())
        editor.putFloat("totalProfit", totalProfit.toFloat())
        editor.apply()
    }

    /**
     * Обновляем UI для суммы портфеля и прибыли, сохраняем в prefs.
     */
    private fun updatePortfolioAndProfit() {
        totalPortfolioTextView.text = "$totalPortfolio ₽"
        totalProfitTextView.text = "$totalProfit ₽"
        saveTotalValuesToPreferences()
    }

    /**
     * Получаем текущую дату в формате dd.MM.yyyy.
     */
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    /**
     * Очищаем аргументы, чтобы не добавлять одну и ту же карту дважды.
     */
    private fun clearBundle() {
        arguments?.clear()
    }
}

