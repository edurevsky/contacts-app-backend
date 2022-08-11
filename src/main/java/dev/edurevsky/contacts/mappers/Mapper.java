package dev.edurevsky.contacts.mappers;

public interface Mapper<In, Out> {

    Out map(In data);
}
