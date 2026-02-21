package model.expression;

import model.exception.MyException;
import model.state.Heap;
import model.state.SymbolTable;
import model.state.TypeEnvironment;
import model.type.SimpleType;
import model.type.Type;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.Value;

public record BinaryOperatorExpression
        (String operator, Expression left, Expression right) implements Expression {
    @Override
    public Value evaluate(SymbolTable symTable, Heap heap) throws MyException {
        var leftTerm = left.evaluate(symTable, heap);
        var rightTerm = right.evaluate(symTable, heap);
        switch (operator) {
            case "+", "-", "*", "/":
                checkTypes(leftTerm, rightTerm, SimpleType.INTEGER);
                var leftValue = (IntegerValue) leftTerm;
                var rightValue = (IntegerValue) rightTerm;
                return evaluateArithmeticExpression(leftValue, rightValue);

            case "&&", "||":
                checkTypes(leftTerm, rightTerm, SimpleType.BOOLEAN);
                var leftValueB = (BooleanValue) leftTerm;
                var rightValueB = (BooleanValue) rightTerm;
                return evaluateBooleanExpression(leftValueB, rightValueB);

            case "<", "<=", "==", "!=", ">", ">=":
                checkTypes(leftTerm, rightTerm, SimpleType.INTEGER);
                var leftValueI = (IntegerValue) leftTerm;
                var rightValueI = (IntegerValue) rightTerm;
                return evaluateRelationalExpression(leftValueI, rightValueI);
        }
        throw new MyException("Unknown operator " + operator);
    }

    @Override
    public Expression deepCopy() {
        return new BinaryOperatorExpression(this.operator, this.left.deepCopy(), this.right.deepCopy());
    }

    @Override
    public Type typecheck(TypeEnvironment typeEnv) throws MyException {
        Type type1, type2;
        type1 = left.typecheck(typeEnv);
        type2 = right.typecheck(typeEnv);

        switch (operator) {
            case "+", "-", "*", "/":
                if (type1 == SimpleType.INTEGER) {
                    if (type2 == SimpleType.INTEGER) {
                        return SimpleType.INTEGER;
                    } else {
                        throw new MyException("Second operand is not an integer");
                    }
                } else {
                    throw new MyException("First operand is not an integer");
                }

            case "&&", "||":
                if (type1 == SimpleType.BOOLEAN) {
                    if (type2 == SimpleType.BOOLEAN) {
                        return SimpleType.BOOLEAN;
                    } else {
                        throw new MyException("Second operand is not a boolean");
                    }
                } else {
                    throw new MyException("First operand is not a boolean");
                }

            case "<", "<=", "==", "!=", ">", ">=":
                if (type1 == SimpleType.INTEGER) {
                    if (type2 == SimpleType.INTEGER) {
                        return SimpleType.BOOLEAN;
                    } else {
                        throw new MyException("Second operand is not an integer");
                    }
                } else {
                    throw new MyException("First operand is not an integer");
                }
        }
        throw new MyException("Unknown operator " + operator);
    }

    private void checkTypes(Value leftTerm, Value rightTerm, SimpleType simpleType) {
        if (leftTerm.getType() != simpleType) {
            throw new MyException("First operand is not of type " + simpleType.toString());
        } else if (rightTerm.getType() != simpleType) {
            throw new MyException("Second operand is not of type " + simpleType.toString());
        }
    }

    private IntegerValue evaluateArithmeticExpression(IntegerValue leftValue, IntegerValue rightValue) {
        try {
            return switch (operator) {
                case "+" -> new IntegerValue(leftValue.value() + rightValue.value());
                case "-" -> new IntegerValue(leftValue.value() - rightValue.value());
                case "*" -> new IntegerValue(leftValue.value() * rightValue.value());
                case "/" -> new IntegerValue(leftValue.value() / rightValue.value());
                default -> throw new MyException("Unreachable code");
            };
        } catch (ArithmeticException e) {
            // if a division by zero occurs, catch the error
            throw new MyException("Division by zero");
        }
    }

    private BooleanValue evaluateBooleanExpression(BooleanValue leftValue, BooleanValue rightValue) {
        return switch (operator) {
            case "&&" -> new BooleanValue(leftValue.value() && rightValue.value());
            case "||" -> new BooleanValue(leftValue.value() || rightValue.value());
            default -> throw new MyException("Unreachable code");
        };
    }

    private Value evaluateRelationalExpression(IntegerValue leftValue, IntegerValue rightValue) {
        return switch (operator) {
            case "<"  -> new BooleanValue(leftValue.value() <  rightValue.value());
            case "<=" -> new BooleanValue(leftValue.value() <= rightValue.value());
            case "==" -> new BooleanValue(leftValue.value() == rightValue.value());
            case "!=" -> new BooleanValue(leftValue.value() != rightValue.value());
            case ">"  -> new BooleanValue(leftValue.value() >  rightValue.value());
            case ">=" -> new BooleanValue(leftValue.value() >= rightValue.value());
            default -> throw new MyException("Unreachable code");
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(left.toString());
        sb.append(" ").append(operator).append(" ");
        sb.append(right.toString());
        sb.append(")");
        return sb.toString();
    }
}
