package dao;

import java.io.Serializable;

public record SerializableUserDetails(String username, String password, String email, String type) implements Serializable {}
