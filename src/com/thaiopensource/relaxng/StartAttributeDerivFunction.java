package com.thaiopensource.relaxng;


class StartAttributeDerivFunction extends StartTagOpenDerivFunction {
  StartAttributeDerivFunction(Name name, PatternBuilder builder) {
    super(name, builder);
  }

  public Object caseElement(ElementPattern p) {
    return getPatternBuilder().makeNotAllowed();
  }

  public Object caseGroup(GroupPattern p) {
    final Pattern p1 = p.getOperand1();
    final Pattern p2 = p.getOperand2();
    return getPatternBuilder().makeChoice(
					  memoApply(p1).applyForPattern(new ApplyAfterFunction(getPatternBuilder()) {
					      Pattern apply(Pattern x) {
						return getPatternBuilder().makeGroup(x, p2);
					      }
					    }),
					  memoApply(p2).applyForPattern(new ApplyAfterFunction(getPatternBuilder()) {
					      Pattern apply(Pattern x) {
						return getPatternBuilder().makeGroup(p1, x);
					      }
                                            }),
                                          true);
  }

  public Object caseAttribute(AttributePattern p) {
    if (!p.getNameClass().contains(getName()))
      return getPatternBuilder().makeNotAllowed();
    return getPatternBuilder().makeAfter(p.getContent(),
					 getPatternBuilder().makeEmpty());
  }

  PatternMemo apply(PatternMemo memo) {
    return memo.startAttributeDeriv(this);
  }
}