using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Buscador_Peliculas
{
    public partial class ListaPeliculasDbContext : DbContext
    {
        // MODELOS
        public virtual DbSet<Pelicula> Peliculas { get; set; }
        public ListaPeliculasDbContext() { }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB;Initial Catalog=BuscadorPeliculas");
        }
    }
}
