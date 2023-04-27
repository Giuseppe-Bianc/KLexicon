package org.dersbian.klexicon

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@BenchmarkMode(Mode.SampleTime, Mode.AverageTime, Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class MyBenchmark {
    @Param("1", "5", "10")
    private var input: Int = 0
    private lateinit var lexer: Lexer

    /**
     * The function returns a string based on the value of the input parameter.
     */
    fun inputLexer(): String = when (input) {
        1 -> INPUT
        5 -> INPUTSSS
        10 -> INPUTSSSS
        else -> ""
    }

    /* Setting up the lexer with the input string. */
    @Setup
    fun setUp() {
        lexer = Lexer(inputLexer())
    }

    /* A benchmark function. */
    @Benchmark
    final fun benchmarkLexer() {
        lexer.lex()
    }
    /*@OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Benchmark
    final fun benchmarkLexerToJSonS() {
        lexer.lexToJson()
    }*/
}