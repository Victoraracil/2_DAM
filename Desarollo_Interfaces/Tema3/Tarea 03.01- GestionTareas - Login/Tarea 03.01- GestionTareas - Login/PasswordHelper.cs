using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;
using System.Threading.Tasks;

namespace Tarea_03._01__GestionTareas___Login
{
    // Esta clase se va a encargar de implementar el método de hash de las contraseñas
    public static class PasswordHelper
    {
        public static string HashPassword(string password)
        {
            // Función que se encarga de generar el Hash de la contraseña que recibe

            // Comprobamos que la contraseña no este vacia
            if (string.IsNullOrEmpty(password))
            {
                // Si esta vacia, devolvemos un string vacio
                return string.Empty;
            }
            else
            {
                // Creamos una instancia del algoritmos SHA-256 para generar el hash, lo usamos dentro de un using para asegurarnos de eliminar la instancia.
                using (SHA256 sha = SHA256.Create())
                {
                    // Converimos el string en un arreglo de bytes con codificacion UTF-8 ya que los algoritmos de hash trabajan con bytes
                    byte[] bytes = Encoding.UTF8.GetBytes(password);
                    // Ejecutamos el SHA sobre la cadena de bytes para calcularlo
                    byte[] hash = sha.ComputeHash(bytes);
                    // Devuelve el hash en formato hexadecimal
                    return Convert.ToHexString(hash);
                }
            }
        }
    }
}