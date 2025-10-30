using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Tarea_03._01__GestionTareas___Login;
using TaskManager.Data.Models;

namespace Tarea_03._01__GestionTareas___Login.Migrations
{
    internal class ServiceUser : IDisposable
    {
        // Campo para saber si ya se liberaron los recursos
        bool disposed;
        // Creamos un constructor vacio
        public ServiceUser()
        {
            // Flag: Se ha llamado al método Dispose?
            disposed = false;
        }



        // METODOS CRUD

        // En cada método instanciamos un objeto de tipo TaskManagerDbContext que representa una conexion nueva con la base de datos
        // tras ejecutar el método, la interfaz IDisposable, lo limpia de memoria y por tanto cierra la conexión con la base de datos para evitar
        // saturaciones en ella.
        public async Task<List<User>> Listar()
        {
            // Listamos todos los usuarios
            using (var _context = new TaskManagerDbContext())
            {
                return await _context.Users
                    .AsNoTracking() // Propiedad de Linq para mejorar el rendimiento cuando no se modifican los datos
                    .OrderBy(u => u.Id) // Ordena los resultado por Id
                    .ToListAsync(); // Ejecuta la consulta y devuelve los resultados, es asincrono por lo que devuelve una Task<List<User>>
            }
        }

        public async Task<User?> Listar(int id)
        {
            // Listamos un usuario por ID
            using (var _context = new TaskManagerDbContext())
            {
                return await _context.Users
                .AsNoTracking()
                .FirstOrDefaultAsync(u => u.Id == id); // Devuelve el primer objeto que cumpla la condicion de la expresión lambda
            }
        }

        public async Task<User?> Listar(string username)
        {
            // Listamos un usuario por Nombre
            using (var _context = new TaskManagerDbContext())
            {
                return await _context.Users
                    .AsNoTracking()
                    .FirstOrDefaultAsync(u => u.Usuario == username);
            }
        }

        public async Task<User> Insertar(User user)
        {
            // Insertamos un nuevo usuario
            if (user == null) throw new ArgumentNullException(nameof(user));

            using (var _context = new TaskManagerDbContext())
            {
                await _context.Users.AddAsync(user); // Agrega un nuevo objeto User al DbSet y lo inserta de forma asincrona
                await _context.SaveChangesAsync(); // Ejecuta todas las operaciones pendientes en la base de datos
                return user;
            }
        }

        public async Task<bool> Actualizar(User user)
        {
            // Actualizamos un usuario existente
            if (user == null) throw new ArgumentNullException(nameof(user));

            using (var _context = new TaskManagerDbContext())
            {
                var existing = await _context.Users.FirstOrDefaultAsync(u => u.Id == user.Id);
                if (existing is null) return false;

                // Actualiza campos permitidos
                existing.Usuario = user.Usuario;
                existing.PasswordHash = user.PasswordHash;
                existing.NombreCompleto = user.NombreCompleto;
                // CreatedAt normalmente no se actualiza

                await _context.SaveChangesAsync(); // Llamamos directamente a SaveChangesAsync gracias a que EF mantiene el objeto EXISTING trakeado con lo que se llama Change Tracking
                return true;
            }
        }

        public async Task<bool> Borrar(int id)
        {
            using (var _context = new TaskManagerDbContext())
            {
                var entity = await _context.Users.FirstOrDefaultAsync(u => u.Id == id);
                if (entity is null) return false;

                _context.Users.Remove(entity); // Marca el objeto entity para ser eliminado en el siguiente SaveChangesAsync()
                await _context.SaveChangesAsync();
                return true;
            }
        }


        // MÉTODOS DE LIBERACIÓN DE RECURSOS ----------

        // Método público de IDisposable
        public void Dispose()
        {
            Dispose(true); // Método que libera los recursos cuando terminas de usar la clase
            GC.SuppressFinalize(this); // Evita llamar al finalizador dos veces
        }

        // Método protegido: libera los recursos
        protected virtual void Dispose(bool disposing)
        {
            if (disposed)
                return;

            if (disposing)
            {
                //Liberar recursos no manejados como ficheros, conexiones a bd, etc.
            }

            disposed = true;
        }

        // Finalizador (por si se olvida llamar a Dispose), tambien conocido como Destructor
        ~ServiceUser()
        {
            Dispose(false);
        }

        //Metodo para comprobar si existe el usuario admi, si no lo crea
        public async Task EnsureAdminUserExists()
        {
            using (var _context = new TaskManagerDbContext())
            {
                var adminUser = _context.Users
                    .FirstOrDefault(u => u.Usuario == "admin");
                if (adminUser == null)
                {
                    var nuevoAdminUser = new User
                    {
                        Usuario = "admin",
                        PasswordHash = PasswordHelper.HashPassword("1234"),
                        NombreCompleto = "admin",
                        CorreoElectronico = "admin@admin.com"

                    };

                    var nuevoUserCreado = Insertar(nuevoAdminUser);

                }
            }
        }
    }
}