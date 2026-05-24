package cl.skyscraper.clients.util;

public class Messages {
    
    // Auth Messages
    public static final String INVALID_BEARER_TOKEN = "Token inválido o no proporcionado";
    public static final String INVALID_REFRESH_TOKEN = "Token de actualización inválido";
    public static final String USER_NOT_FOUND_IN_SESSION = "Usuario no encontrado en sesión";
    
    // Password Change Messages
    public static final String CURRENT_PASSWORD_INCORRECT = "La contraseña actual es incorrecta";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Contraseña cambió exitosamente";
    
    // Validation Messages
    public static final String RUN_CANNOT_BE_NULL = "El RUN no puede ser nulo";
    public static final String DV_CANNOT_BE_BLANK = "El DV no puede estar en blanco";
    public static final String DV_MUST_BE_ONE_CHARACTER = "El DV debe ser exactamente 1 carácter";
    public static final String USERNAME_CANNOT_BE_BLANK = "El nombre de usuario no puede estar en blanco";
    public static final String USERNAME_LENGTH = "El nombre de usuario debe estar entre 3 y 50 caracteres";
    public static final String NAME_CANNOT_BE_BLANK = "El nombre no puede estar en blanco";
    public static final String NAME_LENGTH = "El nombre debe estar entre 1 y 50 caracteres";
    public static final String MIDDLE_NAME_LENGTH = "El segundo nombre no debe exceder 50 caracteres";
    public static final String LAST_NAME_CANNOT_BE_BLANK = "El apellido no puede estar en blanco";
    public static final String LAST_NAME_LENGTH = "El apellido debe estar entre 1 y 50 caracteres";
    public static final String SECOND_LAST_NAME_CANNOT_BE_BLANK = "El segundo apellido no puede estar en blanco";
    public static final String SECOND_LAST_NAME_LENGTH = "El segundo apellido debe estar entre 1 y 50 caracteres";
    public static final String BIRTH_DATE_CANNOT_BE_NULL = "La fecha de nacimiento no puede ser nula";
    public static final String PHONE_NUMBER_CANNOT_BE_BLANK = "El número de teléfono no puede estar en blanco";
    public static final String EMAIL_CANNOT_BE_BLANK = "El correo electrónico no puede estar en blanco";
    public static final String EMAIL_MUST_BE_VALID = "El correo electrónico debe ser válido";
    public static final String EMAIL_LENGTH = "El correo electrónico debe estar entre 1 y 100 caracteres";
    public static final String ADDRESS_CANNOT_BE_BLANK = "La dirección no puede estar en blanco";
    public static final String ADDRESS_LENGTH = "La dirección debe estar entre 1 y 100 caracteres";
    public static final String PASSWORD_CANNOT_BE_BLANK = "La contraseña no puede estar en blanco";
    public static final String PASSWORD_MINIMUM_LENGTH = "La contraseña debe tener al menos 6 caracteres";
    
    // Passenger Validation Messages
    public static final String FIRST_NAME_CANNOT_BE_BLANK = "El nombre no puede estar en blanco";
    public static final String FIRST_NAME_LENGTH = "El nombre debe estar entre 1 y 50 caracteres";
    public static final String SECOND_NAME_CANNOT_BE_BLANK = "El segundo nombre no puede estar en blanco";
    public static final String SECOND_NAME_LENGTH = "El segundo nombre debe estar entre 1 y 50 caracteres";
    public static final String PATERNAL_LAST_NAME_CANNOT_BE_BLANK = "El apellido paterno no puede estar en blanco";
    public static final String PATERNAL_LAST_NAME_LENGTH = "El apellido paterno debe estar entre 1 y 50 caracteres";
    public static final String MATERNAL_LAST_NAME_CANNOT_BE_BLANK = "El apellido materno no puede estar en blanco";
    public static final String MATERNAL_LAST_NAME_LENGTH = "El apellido materno debe estar entre 1 y 50 caracteres";
    public static final String PHONE_NUMBER_MUST_BE_VALID = "El número de teléfono debe ser válido";
    
    // Duplicate Resource Messages
    public static final String PASSENGER_RUN_ALREADY_EXISTS = "El pasajero con RUN: {0} ya existe";
    public static final String PASSENGER_EMAIL_ALREADY_EXISTS = "El pasajero con correo: {0} ya existe";
    
    // Token Messages
    public static final String TOKEN_CANNOT_BE_BLANK = "El token no puede estar en blanco";
    public static final String TOKEN_TYPE_CANNOT_BE_NULL = "El tipo de token no puede ser nulo";
    
    // Review Messages
    public static final String NO_PERMISSION_EDIT_REVIEW = "No tienes permiso para editar esta reseña";
    public static final String NO_PERMISSION_DELETE_REVIEW = "No tienes permiso para eliminar esta reseña";
    
    // General Error Messages
    public static final String UNEXPECTED_ERROR = "Se produjo un error inesperado: {0}";
    public static final String RESOURCE_NOT_FOUND = "Recurso no encontrado";
    
    // Success Messages
    public static final String RESOURCE_CREATED_SUCCESSFULLY = "Recurso creado exitosamente";
    public static final String RESOURCE_UPDATED_SUCCESSFULLY = "Recurso actualizado exitosamente";
    public static final String RESOURCE_DELETED_SUCCESSFULLY = "Recurso eliminado exitosamente";
}
