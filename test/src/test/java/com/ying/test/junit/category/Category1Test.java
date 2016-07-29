package com.ying.test.junit.category;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.ying.test.junit.parameterized.FibonacciTest;

@RunWith(Categories.class)
//@IncludeCategory(Category1.class)
@ExcludeCategory(Category1.class)
@SuiteClasses({TestOne.class, TestTwo.class})

public class Category1Test {

}
