/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.devtools.simple.compiler.expressions;

import com.google.devtools.simple.compiler.Compiler;
import com.google.devtools.simple.compiler.expressions.synthetic.ConversionExpression;
import com.google.devtools.simple.compiler.symbols.FunctionSymbol;
import com.google.devtools.simple.compiler.types.StringType;
import com.google.devtools.simple.compiler.types.Type;
import com.google.devtools.simple.compiler.types.VariantType;

/**
 * This class is the superclass of all arithmetic expressions.
 * 
 * @author Herbert Czymontek
 */
public abstract class ArithmeticExpression extends BinaryExpression {

  /**
   * Creates a new arithmetic expression.
   *
   * @param position  source code start position of expression
   * @param leftOperand  left operand of operation
   * @param rightOperand  right operand of operation
   */
  public ArithmeticExpression(long position, Expression leftOperand, Expression rightOperand) {
    super(position, leftOperand, rightOperand);
  }

  @Override
  public Expression resolve(Compiler compiler, FunctionSymbol currentFunction) {
    super.resolve(compiler, currentFunction);

    Type leftType = leftOperand.type;
    if (!leftType.isScalarType()) {
      return reportScalarTypeNeededError(compiler, leftOperand);
    }

    Type rightType = rightOperand.type;
    if (!rightType.isScalarType()) {
      return reportScalarTypeNeededError(compiler, rightOperand);
    }

    if (!leftType.equals(rightType) || leftType.equals(StringType.stringType)) {
      // TODO: generate better code - avoid use of variants
      type = VariantType.variantType;
      rightOperand = ConversionExpression.convert(compiler, rightOperand, type);
      leftOperand = ConversionExpression.convert(compiler, leftOperand, type);
    } else {
      type = leftType;
    }

    return fold(compiler, currentFunction);
  }
}
