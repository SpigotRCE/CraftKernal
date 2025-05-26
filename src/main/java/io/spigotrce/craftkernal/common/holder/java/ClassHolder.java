package io.spigotrce.craftkernal.common.holder.java;

/**
 * This class holds the class name.
 * It is used to provide access to the class name.
 */
public abstract class ClassHolder {
    /**
     * The class name.
     */
    private final String className;

    /**
     * The class name with the package.
     */
    private final String classNameWithPackage;

    public ClassHolder() {
        this.className = this.getClass().getSimpleName();
        this.classNameWithPackage = this.getClass().getName();
    }

    /**
     * Gets the class name.
     *
     * @return The class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets the class name with the package.
     *
     * @return The class name with the package.
     */
    public String getClassNameWithPackage() {
        return classNameWithPackage;
    }
}
