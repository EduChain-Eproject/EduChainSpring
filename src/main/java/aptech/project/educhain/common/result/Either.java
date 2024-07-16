package aptech.project.educhain.common.result;

class Either<T, F> {
    private final T left;
    private final F right;
    private final boolean isLeft;

    protected Either(T left, F right, boolean isLeft) {
        this.left = left;
        this.right = right;
        this.isLeft = isLeft;
    }

    protected boolean isLeft() {
        return isLeft;
    }

    protected boolean isRight() {
        return !isLeft;
    }

    protected T getLeft() {
        return left;
    }

    protected F getRight() {
        return right;
    }
}
