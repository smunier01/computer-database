package com.excilys.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComputer is a Querydsl query type for Computer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QComputer extends EntityPathBase<Computer> {

    private static final long serialVersionUID = 446481857L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComputer computer = new QComputer("computer");

    public final QCompany company;

    public final DatePath<java.time.LocalDate> discontinued = createDate("discontinued", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> introduced = createDate("introduced", java.time.LocalDate.class);

    public final StringPath name = createString("name");

    public QComputer(String variable) {
        this(Computer.class, forVariable(variable), INITS);
    }

    public QComputer(Path<? extends Computer> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QComputer(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QComputer(PathMetadata metadata, PathInits inits) {
        this(Computer.class, metadata, inits);
    }

    public QComputer(Class<? extends Computer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

