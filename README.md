# regexengine
An implementation of simple java Regex Engine by constructing an NFA(nondeterministic finite automata) using Thompson's construction algorithm from a prefixed regular expression.

## Getting Started
Download the project and add it to your preferred IDE as Maven project.

### Requirements
JDK

## Running
just load the project and run the **src/java/RegexEdit.java** file. it's a simple GUI for testing your regular expression.

---
### Supported Syntax
you can use bellow operators in your expression including parentheses grouping:
```
'*' : closure
'|' : union
concatenation
'()': parentheses grouping
```
#### especial characters:
```
'*' '|' '°' '¿' ')' '(' '⁁'
```

**Note:** Don't use especial characters as literals in your expression because engine doesn't yet supporting escaping.
**Note2:** Engine doesn't support expression validation. be sure to enter an expression with valid syntax.

## Used Algorithms
- Shunting-Yard Algorithm: used to convert regular expressions to Postfix Notation
- Thompson's construction algorithm: used to construct a NFA from the regular expression
## Authors
- Vafa Tarighi
## Acknowledgment
- Thanks to my university professor Mr. Bagherzadeh for encouraging me to write a regex engine: [jamshid bagherzadeh | LinkedIn](https://www.linkedin.com/in/jamshid-bagherzadeh-88644a51/)

- To Gregory Cernera for his two awesome articles on Medium:
[Converting Regular Expressions to Postfix Notation with the Shunting-Yard Algorithm | by Gregory Cernera | Medium](https://gregorycernera.medium.com/converting-regular-expressions-to-postfix-notation-with-the-shunting-yard-algorithm-63d22ea1cf88)
[Visualizing Thompson’s Construction Algorithm for NFAs, step-by-step | by Gregory Cernera | The Startup | Medium](https://medium.com/swlh/visualizing-thompsons-construction-algorithm-for-nfas-step-by-step-f92ef378581b)
