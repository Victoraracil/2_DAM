using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ejemplo_Usuarios_Acceso_Datos
{
    public partial class GestorUsuariosDbContext : DbContext
    {
        // MODELOS
        public virtual DbSet<User> Users { get; set; }
        public GestorUsuariosDbContext() { }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB;Initial Catalog=Tareas_JLF");
        }
    }
}
