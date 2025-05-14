package ru.practicum.shareit.exception;

public class ForbiddenExeption extends RuntimeException {
  public ForbiddenExeption(String message) {
    super(message);
  }
}
