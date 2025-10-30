using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using System.Collections.Generic;
using System.Threading;
using Aracil_Victor_GestionTareas03._01;
namespace TaskManager.Data.Models;
public partial class TaskManagerDbContext : DbContext
{
    /// <summary>
    /// <author> Victor Aracil Gozalvez</author>
    /// </summary>
    public virtual DbSet<User> Users { get; set; }
    public TaskManagerDbContext() { }
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB ; Initial Catalog = Tareas_VAG");
    }
}

