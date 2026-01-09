using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;


namespace Aracil_Victor_EcoCharger.Models
{

    public class ChargingDbContext : DbContext
    {
        public virtual DbSet<Station> Stations { get; set; }
        public virtual DbSet<Charger> Chargers { get; set; }
        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<ChargingSession> ChargingSessions { get; set; }
        public virtual DbSet<Tariff> Tariffs { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Data Source=(localdb)\\MSSQLLocalDB;Initial Catalog=EcoCharger_VAG");
        }
    }

}
