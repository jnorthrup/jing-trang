package com.thaiopensource.relaxng;

class EndTagDerivFunction extends AbstractPatternFunction {
  private final PatternBuilder builder;

  EndTagDerivFunction(PatternBuilder builder) {
    this.builder = builder;
  }

  public Object caseOther(Pattern p) {
    return builder.makeNotAllowed();
  }

  public Object caseChoice(ChoicePattern p) {
    return builder.makeChoice(memoApply(p.getOperand1()),
			      memoApply(p.getOperand2()),
                              true);
  }

  public Object caseAfter(AfterPattern p) {
    if (p.getOperand1().isNullable())
      return p.getOperand2();
    else
      return builder.makeNotAllowed();
  }

  final private Pattern memoApply(Pattern p) {
    return apply(builder.getPatternMemo(p)).getPattern();
  }

  PatternMemo apply(PatternMemo memo) {
    return memo.endTagDeriv(this);
  }
}