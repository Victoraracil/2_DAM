using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using PruebaBD;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace TaskManager.Data.Models;
public partial class TaskManagerDbContext : DbContext
{
    public virtual DbSet<User> Users { get; set; }
    public TaskManagerDbContext() { }
    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB ; Initial Catalog = Tareas");
   }
}

