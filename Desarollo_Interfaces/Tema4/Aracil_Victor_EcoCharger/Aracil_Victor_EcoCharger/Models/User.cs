using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_EcoCharger.Models
{

    public class User
    {
        [Key]
        public int Id { get; set; }

        [Required, MaxLength(100)]
        public string FullName { get; set; }

        [Required, EmailAddress]
        public string Email { get; set; }

        [Required, MaxLength(20)]
        public string RFIDTag { get; set; }

        [Column(TypeName = "decimal(18, 4)")]
        public decimal Balance { get; set; }

        // Relación 1-N
        [InverseProperty("Users")]
        public virtual ICollection<ChargingSession> ChargingSessions { get; set; } = new ObservableCollection<ChargingSession>();
    }


}
