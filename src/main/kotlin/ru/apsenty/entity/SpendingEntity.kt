package ru.apsenty.entity

import jakarta.persistence.*

@Entity
@Table(name = "spending")
class SpendingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    var amount: Double = 0.0,
    var type: String = "",
    var comment: String = ""
)