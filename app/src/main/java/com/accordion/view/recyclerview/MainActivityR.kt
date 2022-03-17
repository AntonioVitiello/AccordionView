package com.accordion.view.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.accordion.view.R
import kotlinx.android.synthetic.main.activity_main_r.*

/**
 * Created by Antonio Vitiello on 13/03/2022.
 */
class MainActivityR : AppCompatActivity() {
    private lateinit var mAdapter: MyAccordionViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_r)
//        setContentView(R.layout.activity_main_r_1)
        initComponents()
    }

    private fun initComponents() {
        mAdapter = MyAccordionViewAdapter(this) { data: String ->
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
        }
        accordionView.setAdapter(mAdapter)

        //when data is ready, may be from REST...
        mAdapter.switchData(generateFakeData())
        accordionView.expandPosition(2)
    }

    private fun generateFakeData() = listOf(
        DataModel(
            "Lorem ipsum",
            listOf(
                "Lorem ipsum dolor sit amet",
                "consectetur adipiscing elit",
                "sed do eiusmod tempor",
                "incididunt ut labore",
                "et dolore magna aliqua",
                "Ut enim ad minim veniam",
                "quis nostrud exercitation",
                "ullamco laboris nisi",
                "ut aliquip ex",
                "ea commodo consequat"
            )
        ),
        DataModel(
            "de Finibus Bonorum et Malorum",
            listOf(
                "Sed ut perspiciatis",
                "unde omnis iste natus",
                "error sit voluptatem",
                "accusantium doloremque laudantium",
                "totam rem aperiam",
                "eaque ipsa quae",
                "ab illo inventore veritatis",
                "et quasi architecto",
                "beatae vitae dicta sunt explicabo"
            )
        ),
        DataModel(
            "I must explain",
            listOf(
                "But I must explain",
                "to you how all this",
                "mistaken idea of",
                "denouncing pleasure",
                "and praising pain",
                "was born and I",
                "will give you",
                "a complete account",
                "of the system",
                "and expound"
            )
        ),
        DataModel(
            "De Finibus Bonorum et Malorum",
            listOf(
                "At vero eos et",
                "accusamus et iusto",
                "odio dignissimos",
                "ducimus qui blanditiis",
                "praesentium voluptatum",
                "deleniti atque corrupti"
            )
        ),
        DataModel(
            "In a free hour",
            listOf(
                "On the other hand",
                "we denounce with",
                "righteous indignation",
                "and dislike men",
                "who are so beguiled",
                "and demoralized",
                "by the charms of pleasure of the moment",
                "so blinded by desire",
                "that they cannot foresee"
            )
        ),
        DataModel(
            "Lorem short",
            listOf(
                "Lorem ipsum dolor sit amet."
            )
        ),
        DataModel(
            "Excepteur sint occaecat",
            listOf(
                "Duis aute irure",
                "dolor in reprehenderit",
                "in voluptate velit",
                "esse cillum dolore",
                "eu fugiat nulla pariatur",
                "Excepteur sint occaecat",
                "cupidatat non proident",
                "sunt in culpa qui officia",
                "deserunt mollit anim",
                "id est laborum"
            )
        ),
        DataModel(
            "These cases are simple",
            listOf(
                "These cases are",
                "perfectly simple and easy",
                "to distinguish",
                "In a free hour",
                "when our power of choice",
                "is untrammelled",
                "and when nothing prevents",
                "our being able to do",
                "what we like best",
                "every pleasure is",
                "to be welcomed",
                "and every pain avoided"
            )
        ),
        DataModel(
            "Et harum quidem rerum",
            listOf(
                "Et harum quidem rerum",
                "facilis est et expedita",
                "distinctio",
                "Nam libero tempore",
                "cum soluta nobis",
                "est eligendi optio",
                "cumque nihil impedit",
                "quo minus id quod",
                "maxime placeat",
                "facere possimus",
                "omnis voluptas assumenda est",
                "omnis dolor repellendus"
            )
        ),
        DataModel(
            "Temporibus autem quibusdam",
            listOf(
                "Temporibus autem quibusdam",
                "et aut officiis debitis",
                "aut rerum necessitatibus",
                "saepe eveniet",
                "ut et voluptates",
                "repudiandae sint et",
                "molestiae non recusandae",
                "Itaque earum rerum",
                "hic tenetur a",
                "sapiente delectus",
                "ut aut reiciendis",
                "voluptatibus maiores",
                "alias consequatur aut perferendis",
                "doloribus asperiores repellat"
            )
        )
    )

}