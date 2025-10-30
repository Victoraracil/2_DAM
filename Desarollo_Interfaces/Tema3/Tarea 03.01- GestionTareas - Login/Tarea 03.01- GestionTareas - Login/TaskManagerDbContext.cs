using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using System.Threading;
using Tarea_03._01__GestionTareas___Login;
namespace TaskManager.Data.Models;
public partial class TaskManagerDbContext : DbContext
{
    public virtual DbSet<User> Users { get; set; }
    public TaskManagerDbContext() { }
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB ; Initial Catalog = Tareas_VAG");
   }
}

