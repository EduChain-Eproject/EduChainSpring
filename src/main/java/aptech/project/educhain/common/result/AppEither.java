package aptech.project.educhain.common.result;

public class AppEither<L, R> extends Either<L, R> {

    private AppEither(L left, R right, boolean isSuccess) {
        super(left, right, isSuccess);
    }

    public static <L, R> AppEither<L, R> setLeft(L left) {
        return new AppEither<>(left, null, true);
    }

    public static <L, R> AppEither<L, R> setRight(R right) {
        return new AppEither<>(null, right, false);
    }

    public boolean isLeft() {
        return this.isLeft();
    }

    public boolean isRight() {
        return this.isRight();
    }

    public L getLeft() {
        return this.getLeft();
    }

    public R getRight() {
        return this.getRight();
    }

}