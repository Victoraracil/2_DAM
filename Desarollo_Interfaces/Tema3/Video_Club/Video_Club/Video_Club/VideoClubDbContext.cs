using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Video_Club
{
    public partial class VideoClubDbContext : DbContext
    {
        // MODELOS
        public virtual DbSet<Cliente> Clientes { get; set; }
        public virtual DbSet<Pelicula> Peliculas { get; set; }
        public virtual DbSet<Categoria> Categorias { get; set; }
        public VideoClubDbContext() { }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB;Initial Catalog=VideoClub");
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Configuración de relación muchos a muchos entre Cliente y Categoria
            modelBuilder.Entity<Cliente>()
                .HasMany(c => c.Categorias)
                .WithMany(cat => cat.Clientes)
                .UsingEntity(j => j.ToTable("ClienteCategoria"));

            base.OnModelCreating(modelBuilder);
        }
    }
}
