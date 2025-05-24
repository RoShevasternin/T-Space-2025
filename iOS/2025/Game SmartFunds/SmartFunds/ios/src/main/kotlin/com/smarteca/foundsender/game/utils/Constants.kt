package com.smarteca.foundsender.game.utils

import com.smarteca.foundsender.game.data.DataQuiz

const val WIDTH_UI  = 1179f
const val HEIGHT_UI = 2556f

const val TIME_ANIM_SCREEN = 0.4f

const val PREF_KEY_ACCESS = "pref_key_access"

val GLOBAL_listTestTitle = listOf(
    "Basic Financial Concepts",
    "Banking & Credit",
    "Investing & Markets",
    "Personal Finance & Economy",
)

val GLOBAL_listDataQuiz = listOf(
    // 0
    DataQuiz(
        listQ = listOf(
            "What is the term for money owed by a person or company?",
            "What is the opposite of inflation?",
            "Which is a type of investment?",
            "What is the main function of a budget?",
            "Which financial instrument represents company ownership?",
            "What does ROI stand for?",
            "Which is a liquid asset?",
            "What is a financial cushion called?",
            "What is earned on savings over time?",
            "Which term refers to a decrease in value?"
        ),
        listA = listOf(
            listOf("Debt", "Asset", "Profit", "Equity"),
            listOf("Deflation", "Recession", "Expansion", "Depression"),
            listOf("Stock", "Loan", "Debt", "Budget"),
            listOf("Planning", "Borrowing", "Investing", "Taxing"),
            listOf("Stock", "Bond", "Loan", "Credit"),
            listOf("Return on Investment", "Rate of Inflation", "Risk of Interest", "Revenue Over Income"),
            listOf("Cash", "House", "Land", "Gold"),
            listOf("Emergency Fund", "Stock Portfolio", "Fixed Asset", "Loan Plan"),
            listOf("Interest", "Debt", "Loan", "Expense"),
            listOf("Depreciation", "Appreciation", "Inflation", "Expansion")
        )
    ),
    // 1
    DataQuiz(
        listQ = listOf(
            "What is a mortgage used for?",
            "What does APR stand for?",
            "Which affects your credit score?",
            "What is a credit limit?",
            "What does a bank charge for a loan?",
            "What is a secured loan backed by?",
            "What is the purpose of a credit card?",
            "Which payment method involves borrowing?",
            "What is a minimum balance requirement?",
            "Which term refers to ATM fees?"
        ),
        listA = listOf(
            listOf("Buying a house", "Buying stocks", "Renting a car", "Paying taxes"),
            listOf("Annual Percentage Rate", "Annual Profit Return", "Adjusted Payment Ratio", "Account Payment Rule"),
            listOf("Late Payments", "More Income", "High Savings", "Big Purchases"),
            listOf("Maximum loan amount", "Minimum deposit", "Fixed tax rate", "Bank fee"),
            listOf("Interest", "Dividend", "Salary", "Profit"),
            listOf("Collateral", "Trust", "Reputation", "Salary"),
            listOf("Borrowing money", "Storing cash", "Paying taxes", "Investing funds"),
            listOf("Credit Card", "Debit Card", "Cash", "Gift Card"),
            listOf("Lowest account balance", "Maximum withdrawal limit", "Loan approval condition", "Investment profit"),
            listOf("Withdrawal Fee", "Loan Interest", "Account Tax", "Investment Charge")
        )
    ),
    // 2
    DataQuiz(
        listQ = listOf(
            "Which market sells company shares?",
            "What is diversification in investing?",
            "What is a high-risk investment called?",
            "What is an ETF?",
            "Which is a bond feature?",
            "Which investment is least risky?",
            "Which stock market trend is positive?",
            "What is the main goal of investing?",
            "Which financial instrument pays dividends?",
            "Which investment is digital currency?"
        ),
        listA = listOf(
            listOf("Stock Market", "Loan Market", "Tax Market", "Bond Market"),
            listOf("Risk spreading", "Profit doubling", "Tax reduction", "Savings growth"),
            listOf("Speculation", "Budgeting", "Fixed Deposit", "Salary Plan"),
            listOf("Exchange-Traded Fund", "Emergency Tax Fund", "Equity Trading Fee", "Economic Transfer Fund"),
            listOf("Fixed Interest", "Company Ownership", "Daily Trading", "No Risk"),
            listOf("Government Bond", "Stock", "Real Estate", "Cryptocurrency"),
            listOf("Bull Market", "Bear Market", "Crash Market", "Stagnant Market"),
            listOf("Profit Growth", "Debt Creation", "Fixed Spending", "Tax Evasion"),
            listOf("Stocks", "Bonds", "Loans", "Mortgages"),
            listOf("Cryptocurrency", "Real Estate", "Mutual Fund", "Gold")
        )
    ),
    // 3
    DataQuiz(
        listQ = listOf(
            "What is personal wealth management called?",
            "Which tax is deducted from salary?",
            "Which expense is non-essential?",
            "What is a high credit score considered?",
            "Which is a fixed expense?",
            "What does GDP measure?",
            "What is a period of economic decline called?",
            "Which currency is strongest historically?",
            "What is an emergency fund for?",
            "Which financial habit is best?"
        ),
        listA = listOf(
            listOf("Financial Planning", "Tax Filing", "Loan Borrowing", "Credit Scoring"),
            listOf("Income Tax", "Property Tax", "Sales Tax", "Dividend Tax"),
            listOf("Entertainment", "Rent", "Groceries", "Insurance"),
            listOf("750+", "400", "500", "600"),
            listOf("Rent", "Groceries", "Electricity", "Shopping"),
            listOf("Economic Output", "Stock Market", "Bank Deposits", "Personal Wealth"),
            listOf("Recession", "Inflation", "Expansion", "Boom"),
            listOf("US Dollar", "Euro", "Yen", "Peso"),
            listOf("Unexpected expenses", "Shopping", "Vacations", "Loan payments"),
            listOf("Saving Early", "Spending More", "Borrowing Often", "Ignoring Budget")
        )
    ),
)
