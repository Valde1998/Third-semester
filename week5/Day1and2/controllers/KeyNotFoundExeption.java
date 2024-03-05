package Day1and2.controllers;


public class KeyNotFoundExeption extends RuntimeException {

    public KeyNotFoundExeption(String msg) {
        super(msg);
    }

}